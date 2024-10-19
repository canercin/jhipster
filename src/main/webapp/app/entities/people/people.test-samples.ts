import { IPeople, NewPeople } from './people.model';

export const sampleWithRequiredData: IPeople = {
  id: 11456,
};

export const sampleWithPartialData: IPeople = {
  id: 1145,
  firstname: 'into',
  lastname: 'lady eek to',
};

export const sampleWithFullData: IPeople = {
  id: 12735,
  firstname: 'pepper jiggle bid',
  lastname: 'shiny',
};

export const sampleWithNewData: NewPeople = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
