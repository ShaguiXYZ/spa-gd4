import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { IWarehouseModel } from '../../core/models/warehouse.model';
import { WarehouseService } from '../../core/services/warehouse.service';
import { WarehouseComponent } from './warehouse.component';
import { HttpClient } from '@angular/common/http';

describe('WarehouseComponent', () => {
  let component: WarehouseComponent;
  let fixture: ComponentFixture<WarehouseComponent>;
  let warehouseServiceSpy: jasmine.SpyObj<WarehouseService>;
  let routerSpy: jasmine.SpyObj<Router>;

  let mockWarehouses: IWarehouseModel[] = [];

  beforeEach(async () => {
    const httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    warehouseServiceSpy = jasmine.createSpyObj('WarehouseService', ['getWarehouses', 'delete', 'update', 'create']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [WarehouseComponent, ReactiveFormsModule],
      declarations: [],
      providers: [
        FormBuilder,
        { provide: HttpClient, useValue: httpClientSpy },
        { provide: WarehouseService, useValue: warehouseServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    TestBed.overrideComponent(WarehouseComponent, {
      set: {
        providers: [{ provide: WarehouseService, useValue: warehouseServiceSpy }]
      }
    });

    mockWarehouses = [
      { id: 1, uuid: '1', client: 'Client1', size: 100, family: 'EST' },
      { id: 2, uuid: '2', client: 'Client2', size: 200, family: 'ROB' }
    ];

    fixture = TestBed.createComponent(WarehouseComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch warehouses on init', fakeAsync(() => {
    warehouseServiceSpy.getWarehouses.and.returnValue(Promise.resolve(mockWarehouses));
    component.ngOnInit();
    tick();

    expect(component.$warehouses()).toEqual(mockWarehouses);
    expect(warehouseServiceSpy.getWarehouses).toHaveBeenCalledWith(0, 10);
  }));

  it('should handle error when fetching warehouses', fakeAsync(() => {
    const error = new Error('Fetch error');
    spyOn(console, 'error');
    warehouseServiceSpy.getWarehouses.and.returnValue(Promise.reject(error));
    component.ngOnInit();
    tick();

    expect(console.error).toHaveBeenCalledWith('Error fetching warehouses:', error);
  }));

  it('should patch form and set selectedWarehouse on editData', () => {
    component['createForm']();
    const warehouse = mockWarehouses[0];
    component.editData(warehouse);

    expect(component.form.value.client).toBe(warehouse.client);
    expect(component.form.value.size).toBe(warehouse.size);
    expect(component.form.value.family).toBe(warehouse.family);
    expect(component['selectedWarehouse']).toBe(warehouse);
  });

  it('should delete warehouse and reset form', fakeAsync(() => {
    component.$warehouses.set(mockWarehouses);
    component['createForm']();
    warehouseServiceSpy.delete.and.returnValue(Promise.resolve());
    spyOn(component, 'resetForm');
    component.deleteData('1');
    tick();

    expect(component.$warehouses()).toEqual([mockWarehouses[1]]);
    expect(component.resetForm).toHaveBeenCalled();
    expect(warehouseServiceSpy.delete).toHaveBeenCalledWith('1');
  }));

  it('should handle error when deleting warehouse', fakeAsync(() => {
    const error = new Error('Delete error');
    spyOn(console, 'error');
    warehouseServiceSpy.delete.and.returnValue(Promise.reject(error));
    component.deleteData('1');
    tick();

    expect(console.error).toHaveBeenCalledWith('Error deleting warehouse:', error);
  }));

  it('should reset form and selectedWarehouse', () => {
    component['createForm']();
    component['selectedWarehouse'] = mockWarehouses[0];
    component.form.setValue({ client: 'X', size: 1, family: 'ROB' });
    component.resetForm();

    expect(component.form.pristine).toBeTrue();
    expect(component.form.untouched).toBeTrue();
    expect(component['selectedWarehouse']).toBeNull();
  });

  it('should update warehouse on submitForm when editing', fakeAsync(() => {
    component['createForm']();
    component.$warehouses.set(mockWarehouses);
    component['selectedWarehouse'] = mockWarehouses[0];
    component.form.setValue({ client: 'Updated', size: 150, family: 'A' });
    const updatedWarehouse = { ...mockWarehouses[0], client: 'Updated', size: 150 };
    warehouseServiceSpy.update.and.returnValue(Promise.resolve(updatedWarehouse));
    spyOn(component, 'resetForm');
    component.submitForm();
    tick();

    expect(component.$warehouses()[0].client).toBe('Updated');
    expect(component.resetForm).toHaveBeenCalled();
    expect(warehouseServiceSpy.update).toHaveBeenCalled();
  }));

  it('should handle error when updating warehouse', fakeAsync(() => {
    component['createForm']();
    component['selectedWarehouse'] = mockWarehouses[0];
    warehouseServiceSpy.update.and.returnValue(Promise.reject('Update error'));
    spyOn(console, 'error');
    component.submitForm();
    tick();

    expect(console.error).toHaveBeenCalledWith('Error updating warehouse:', 'Update error');
  }));

  it('should create warehouse on submitForm when not editing', fakeAsync(() => {
    component['createForm']();
    component.$warehouses.set([]);
    component.form.setValue({ client: 'New', size: 300, family: 'ROB' });
    const newWarehouse: IWarehouseModel = { id: 3, uuid: '3', client: 'New', size: 300, family: 'ROB' };
    warehouseServiceSpy.create.and.returnValue(Promise.resolve(newWarehouse));
    spyOn(component, 'resetForm');
    component.submitForm();
    tick();

    expect(component.$warehouses()).toContain(newWarehouse);
    expect(component.resetForm).toHaveBeenCalled();
    expect(warehouseServiceSpy.create).toHaveBeenCalled();
  }));

  it('should handle error when creating warehouse', fakeAsync(() => {
    component['createForm']();
    warehouseServiceSpy.create.and.returnValue(Promise.reject('Create error'));
    spyOn(console, 'error');
    component.submitForm();
    tick();

    expect(console.error).toHaveBeenCalledWith('Error creating warehouse:', 'Create error');
  }));

  it('should navigate to racks', () => {
    component.racks('123');

    expect(routerSpy.navigate).toHaveBeenCalledWith(['/rack', '123']);
  });

  it('should create form with correct controls', () => {
    component['createForm']();

    expect(component.form.contains('client')).toBeTrue();
    expect(component.form.contains('size')).toBeTrue();
    expect(component.form.contains('family')).toBeTrue();
  });
});
