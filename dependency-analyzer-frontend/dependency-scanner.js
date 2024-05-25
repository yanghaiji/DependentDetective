const { exec } = require('child_process');
const path = require('path');
const fs = require('fs');
const xlsx = require('xlsx');

function runCommand(command, cwd) {
  return new Promise((resolve, reject) => {
    exec(command, { cwd, shell: true }, (error, stdout, stderr) => {
      if (error) {
        console.error(`Error executing command: ${command}`);
        console.error(`Error details: ${stderr}`);
        reject(error);
      } else {
        resolve(stdout || stderr);
      }
    });
  });
}

async function getAllDependencies(projectPath) {
  try {
    console.log(`Getting dependencies for project at ${projectPath}...`);

    const npmListOutput = await runCommand('npm list --json --depth=0', projectPath);
    const dependencies = JSON.parse(npmListOutput).dependencies;
    if (!dependencies) {
      throw new Error('Failed to retrieve dependencies.');
    }

    return Object.entries(dependencies).map(([name, info]) => ({ framework: name, version: info.version }));

  } catch (error) {
    console.error('Error:', error);
    return [];
  }
}

function isNodeProject(directory) {
  return fs.existsSync(path.join(directory, 'package.json'));
}

async function scanDirectory(basePath) {
  const allDependencies = [];

  async function analyzeProject(projectPath, projectName) {
    const dependencies = await getAllDependencies(projectPath);
    dependencies.forEach((dep, index) => {
      allDependencies.push({
        project: index === 0 ? projectName : '',
        framework: dep.framework,
        version: dep.version,
        framework_with_version: `${dep.framework}@${dep.version}`
      });
    });
  }

  async function scan(basePath) {
    const entries = fs.readdirSync(basePath, { withFileTypes: true });
    for (const entry of entries) {
      const fullPath = path.join(basePath, entry.name);
      if (entry.isDirectory()) {
        if (isNodeProject(fullPath)) {
          await analyzeProject(fullPath, entry.name);
        } else {
          await scan(fullPath);
        }
      }
    }
  }

  await scan(basePath);

  // Create a single worksheet
  const ws = xlsx.utils.json_to_sheet(allDependencies);

  // Create a workbook and append the worksheet
  const workbook = xlsx.utils.book_new();
  xlsx.utils.book_append_sheet(workbook, ws, 'Dependencies');

  // Write workbook to a file
  const excelFilePath = path.join(__dirname, 'dependencies.xlsx');
  xlsx.writeFile(workbook, excelFilePath);

  console.log(`Excel file generated: ${excelFilePath}`);
}

const targetPath = process.argv[2];
if (!targetPath) {
  console.error('Please provide a directory to scan.');
  process.exit(1);
}

scanDirectory(targetPath);
