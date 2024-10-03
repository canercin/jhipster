import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 23892,
  login: 'Y6CJf',
};

export const sampleWithPartialData: IUser = {
  id: 3204,
  login: 'Q@YaN\\9Tfzxv\\beFwCh\\b4E',
};

export const sampleWithFullData: IUser = {
  id: 13030,
  login: 'DPYk',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
