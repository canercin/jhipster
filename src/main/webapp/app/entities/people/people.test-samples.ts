import { IPeople, NewPeople } from './people.model';

export const sampleWithRequiredData: IPeople = {
  id: 13143,
};

export const sampleWithPartialData: IPeople = {
  id: 7207,
  firstname: 'unless coolly',
  lastname: 'unlike negotiation when',
};

export const sampleWithFullData: IPeople = {
  id: 8110,
  firstname: 'joyfully',
  lastname: 'cautious quaver quarrelsomely',
};

export const sampleWithNewData: NewPeople = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
