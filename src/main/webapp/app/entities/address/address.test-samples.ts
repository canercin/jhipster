import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 15875,
};

export const sampleWithPartialData: IAddress = {
  id: 29481,
  street: 'W 4th Avenue',
  state: 'seemingly excepting',
  zip: 'aw anti',
};

export const sampleWithFullData: IAddress = {
  id: 3025,
  street: 'Myrl Courts',
  city: 'New Alvertaton',
  state: 'haversack meaningfully nocturnal',
  zip: 'cautious',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
