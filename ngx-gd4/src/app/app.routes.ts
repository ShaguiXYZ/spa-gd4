import { Routes } from '@angular/router';

export enum AppUrls {
  _warehouse = 'warehouse',
  _rack = 'rack',
  home = 'warehouse',
  root = '**'
}
export const routes: Routes = [
  {
    path: AppUrls.home,
    loadComponent: () => import('./modules/warehouse/warehouse.component').then(c => c.WarehouseComponent)
  },
  {
    path: `${AppUrls._rack}/:warehouseId`,
    loadComponent: () => import('./modules/rack/rack.component').then(c => c.RackComponent)
  },
  {
    path: AppUrls.root,
    pathMatch: 'full',
    redirectTo: AppUrls.home
  }
];
