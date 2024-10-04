import { IPeople, NewPeople } from './people.model';

export const sampleWithRequiredData: IPeople = {
  id: 23559,
};

export const sampleWithPartialData: IPeople = {
  id: 22287,
  lastname: 'limping',
};

export const sampleWithFullData: IPeople = {
  id: 29100,
  firstname: 'er canter',
  lastname: 'jellyfish minty',
};

export const sampleWithNewData: NewPeople = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
