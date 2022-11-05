module.exports = {
    'env': {
        'browser': true,
        'es2021': true,
    },
    'extends': [
        'plugin:react/recommended',
        'google',
    ],
    'parserOptions': {
        'ecmaFeatures': {
            'jsx': true,
        },
        'ecmaVersion': 'latest',
        'sourceType': 'module',
    },
    'plugins': [
        'react',
    ],
    'rules': {
        'semi': ['warn', 'always'],
        'quotes': ['warn', 'single'],
        'indent': ['error', 4],
        'eqeqeq': 'error',
        'camelcase': 'error',
        'space-infix-ops': 'error',
        'no-console': 'error',
        'linebreak-style': ['error', 'windows'],
    },
};
