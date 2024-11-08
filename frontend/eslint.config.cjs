import eslintPluginReact from "eslint-plugin-react";
import { FlatCompat } from "@eslint/eslintrc";
const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: typeScriptEsLintPlugin.configs["recommended"],
});

export default [
  {
    files: ["**/*.js", "**/*.jsx", "**/*.ts", "**/*.tsx"],
    plugins: {
      react: eslintPluginReact,
    },
    ...compat.extends("standard", "example"),
    parserOptions: {
      ecmaVersion: 2020,
      sourceType: "module",
    },
    rules: {},
  },
];
