import '@testing-library/jest-dom';
jest.mock('axios');

import { TextEncoder, TextDecoder } from 'util';

global.TextEncoder = TextEncoder as any;
global.TextDecoder = TextDecoder as any;
