import { IWarehouse } from 'app/entities/warehouse/warehouse.model';

export interface IProduct {
  id: number;
  name?: string | null;
  description?: string | null;
  price?: number | null;
  warehouses?: IWarehouse[] | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
