import js from '@eslint/js';
import react from 'eslint-plugin-react';
import tseslint from '@typescript-eslint/eslint-plugin';
import parser from '@typescript-eslint/parser';
import prettier from 'eslint-config-prettier';

export default [
  {
    files: ['**/*.{ts,tsx}'],
    languageOptions: {
      parser,
      parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
        ecmaFeatures: { jsx: true },
      },
    },
    plugins: {
      react,
      '@typescript-eslint': tseslint,
    },
    settings: { react: { version: 'detect' } },
    rules: {},
  },
  {
    ...js.configs.recommended,
    rules: {
      ...js.configs.recommended.rules,
      ...prettier.rules,
    },
  },
];
