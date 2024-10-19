import { IProduct } from 'app/entities/product/product.model';

export interface IWarehouse {
  id: number;
  name?: string | null;
  address?: string | null;
  products?: IProduct[] | null;
}

export type NewWarehouse = Omit<IWarehouse, 'id'> & { id: null };
