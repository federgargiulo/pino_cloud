const fs = require("fs");
const path = require("path");

const MIXIN_DIR = "./src/scss/material-config";

function toScssFile(name) {
  return `_${name}.scss`;
}

function createForwardFile(dir) {
  const files = fs
    .readdirSync(dir)
    .filter(
      (f) =>
        f.endsWith(".scss") &&
        f !== "index.scss" &&
        f !== "_index.scss" &&
        !f.startsWith(".")
    )
    .map((f) => path.basename(f, ".scss").replace(/^_/, ""));

  const forwardLines = files.map((f) => `@forward '${f}';\n`).join("");

  fs.writeFileSync(path.join(dir, "_index.scss"), forwardLines);
  console.log("✅ Creato:", path.join(dir, "_index.scss"));
}

function cleanOldIndex(dir) {
  const oldIndex = path.join(dir, "index.scss");
  if (fs.existsSync(oldIndex)) {
    fs.renameSync(oldIndex, oldIndex + ".bak");
    console.log("🔁 Rinominato vecchio index.scss → index.scss.bak");
  }
}

// Esegui
createForwardFile(MIXIN_DIR);
cleanOldIndex(MIXIN_DIR);
