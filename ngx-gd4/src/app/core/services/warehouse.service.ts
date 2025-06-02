import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from '../../enviroments/environment';
import { Page } from '../models/page.model';
import { IWarehouseDTO, IWarehouseModel } from '../models/warehouse.model';

@Injectable()
export class WarehouseService {
  private _url = `${environment.baseUrl}/api/warehouse`;

  private readonly httpClient = inject(HttpClient);

  public getWarehouses(page: number, size: number): Promise<IWarehouseModel[]> {
    const httpParams = new HttpParams().appendAll({ page, size });

    return firstValueFrom(
      this.httpClient.get<Page<IWarehouseDTO>>(`${this._url}/getAll`, { params: httpParams }).pipe(
        map(page => page.content),
        map((dtos: IWarehouseDTO[]) => dtos.map(IWarehouseDTO.toModel))
      )
    );
  }

  public create(warehouse: IWarehouseModel): Promise<IWarehouseModel> {
    return firstValueFrom(
      this.httpClient.post<IWarehouseDTO>(`${this._url}/create`, IWarehouseDTO.fromModel(warehouse)).pipe(map(IWarehouseDTO.toModel))
    );
  }

  public update(warehouse: IWarehouseModel): Promise<IWarehouseModel> {
    console.log('Updating warehouse:', warehouse);

    return firstValueFrom(
      this.httpClient
        .put<IWarehouseDTO>(`${this._url}/update/${warehouse.uuid}`, IWarehouseDTO.fromModel(warehouse))
        .pipe(map(IWarehouseDTO.toModel))
    );
  }

  public delete(uuid: string): Promise<void> {
    return firstValueFrom(this.httpClient.delete<void>(`${this._url}/delete/${uuid}`));
  }
}
