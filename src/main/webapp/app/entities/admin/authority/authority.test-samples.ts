import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'df40abf8-2b02-440b-8b94-a56342d3ced0',
};

export const sampleWithPartialData: IAuthority = {
  name: '1c304f2c-01c5-4873-b396-f7c36ecb2e1f',
};

export const sampleWithFullData: IAuthority = {
  name: '56b08506-5d62-4f20-8fe5-64cea70ab2b7',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
