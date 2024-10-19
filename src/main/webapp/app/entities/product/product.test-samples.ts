import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 25029,
};

export const sampleWithPartialData: IProduct = {
  id: 23068,
};

export const sampleWithFullData: IProduct = {
  id: 17205,
  name: 'out boo oh',
  description: 'masticate',
  price: 2553.51,
};

export const sampleWithNewData: NewProduct = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
