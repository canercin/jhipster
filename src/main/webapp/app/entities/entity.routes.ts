import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'people',
    data: { pageTitle: 'People' },
    loadChildren: () => import('./people/people.routes'),
  },
  {
    path: 'address',
    data: { pageTitle: 'Addresses' },
    loadChildren: () => import('./address/address.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
