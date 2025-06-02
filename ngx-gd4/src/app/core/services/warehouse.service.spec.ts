import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from '../../enviroments/environment';
import { IWarehouseDTO, IWarehouseModel } from '../models/warehouse.model';
import { WarehouseService } from './warehouse.service';
import { provideHttpClient } from '@angular/common/http';

describe('WarehouseService', () => {
  let service: WarehouseService;
  let httpMock: HttpTestingController;

  const baseUrl = `${environment.baseUrl}/api/warehouse`;

  const warehouseDto: IWarehouseDTO = {
    id: 1,
    uuid: '1',
    client: 'client',
    family: 'ROB',
    size: 100
  } as IWarehouseDTO;

  const warehouseModel: IWarehouseModel = {
    id: 1,
    uuid: '1',
    client: 'client',
    family: 'ROB',
    size: 100
  } as IWarehouseModel;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [WarehouseService, provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(WarehouseService);
    httpMock = TestBed.inject(HttpTestingController);

    // Mock static methods for DTO/model conversion
    spyOn(IWarehouseDTO, 'toModel').and.callFake((dto: IWarehouseDTO) => warehouseModel);
    spyOn(IWarehouseDTO, 'fromModel').and.callFake((model: IWarehouseModel) => warehouseDto);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should get warehouses', async () => {
    const page = 0,
      size = 10;
    const mockResponse = {
      content: [warehouseDto],
      totalElements: 1,
      totalPages: 1,
      number: 0,
      size: 10
    };
    const promise = service.getWarehouses(page, size);
    const req = httpMock.expectOne(r => r.method === 'GET' && r.url === `${baseUrl}/getAll`);

    expect(req.request.params.get('page')).toBe(page.toString());
    expect(req.request.params.get('size')).toBe(size.toString());

    req.flush(mockResponse);

    const result = await promise;

    expect(result).toEqual([warehouseModel]);
  });

  it('should create a warehouse', async () => {
    const promise = service.create(warehouseModel);
    const req = httpMock.expectOne(`${baseUrl}/create`);

    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(warehouseDto);

    req.flush(warehouseDto);

    const result = await promise;
    expect(result).toEqual(warehouseModel);
  });

  it('should update a warehouse', async () => {
    const promise = service.update(warehouseModel);
    const req = httpMock.expectOne(`${baseUrl}/update/${warehouseModel.uuid}`);

    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(warehouseDto);

    req.flush(warehouseDto);

    const result = await promise;
    expect(result).toEqual(warehouseModel);
  });

  it('should delete a warehouse', async () => {
    const uuid = '1';
    const promise = service.delete(uuid);
    const req = httpMock.expectOne(`${baseUrl}/delete/${uuid}`);

    expect(req.request.method).toBe('DELETE');

    req.flush(null);

    await expectAsync(promise).toBeResolved();
  });
});
