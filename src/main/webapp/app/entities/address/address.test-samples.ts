import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 27125,
};

export const sampleWithPartialData: IAddress = {
  id: 18971,
  street: 'Schmitt Corners',
  state: 'qua',
  zip: 'gleefully',
};

export const sampleWithFullData: IAddress = {
  id: 4265,
  street: 'Leuschke Corner',
  city: 'East Joshua',
  state: 'notwithstanding effector',
  zip: 'bah',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
