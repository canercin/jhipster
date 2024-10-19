import { IWarehouse, NewWarehouse } from './warehouse.model';

export const sampleWithRequiredData: IWarehouse = {
  id: 25681,
};

export const sampleWithPartialData: IWarehouse = {
  id: 31288,
};

export const sampleWithFullData: IWarehouse = {
  id: 26947,
  name: 'uh-huh electronics ack',
  address: 'blindly',
};

export const sampleWithNewData: NewWarehouse = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
