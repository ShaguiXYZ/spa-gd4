import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom, map } from 'rxjs';
import { environment } from '../../enviroments/environment';
import { Page } from '../models/page.model';
import { IRackDTO, IRackModel } from '../models/rack.model';

@Injectable()
export class RackService {
  private _url = `${environment.baseUrl}/api/rack`;

  private readonly httpClient = inject(HttpClient);

  public getRacksByWarehouse(warehouseUuid: string, page: number, size: number): Promise<IRackModel[]> {
    const httpParams = new HttpParams().appendAll({ page, size });

    return firstValueFrom(
      this.httpClient.get<Page<IRackDTO>>(`${this._url}/getAll/${warehouseUuid}`, { params: httpParams }).pipe(
        map(page => page.content),
        map((dtos: IRackDTO[]) => dtos.map(IRackDTO.toModel))
      )
    );
  }

  public create(warehouseUuid: string, rack: IRackModel): Promise<IRackModel> {
    return firstValueFrom(
      this.httpClient.post<IRackDTO>(`${this._url}/create/${warehouseUuid}`, IRackDTO.fromModel(rack)).pipe(map(IRackDTO.toModel))
    );
  }

  public update(warehouseUuid: string, rack: IRackModel): Promise<IRackModel> {
    console.log('Updating Rack:', rack);

    return firstValueFrom(
      this.httpClient
        .put<IRackDTO>(`${this._url}/update/${warehouseUuid}/${rack.uuid}`, IRackDTO.fromModel(rack))
        .pipe(map(IRackDTO.toModel))
    );
  }

  public delete(uuid: string): Promise<void> {
    return firstValueFrom(this.httpClient.delete<void>(`${this._url}/delete/${uuid}`));
  }
}
