import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { IRackModel } from '../../core/models/rack.model';
import { RackService } from '../../core/services/rack.service';
import { RackComponent } from './rack.component';

describe('RackComponent', () => {
  let component: RackComponent;
  let fixture: ComponentFixture<RackComponent>;
  let rackServiceSpy: jasmine.SpyObj<RackService>;
  let routerSpy: jasmine.SpyObj<Router>;

  let mockRacks: IRackModel[] = [];

  beforeEach(async () => {
    const httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    rackServiceSpy = jasmine.createSpyObj('RackService', ['getRacksByWarehouse', 'delete', 'update', 'create']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    // Configuramos el comportamiento por defecto para que siempre devuelva una promesa
    rackServiceSpy.getRacksByWarehouse.and.returnValue(Promise.resolve([]));

    await TestBed.configureTestingModule({
      imports: [RackComponent, ReactiveFormsModule],
      declarations: [],
      providers: [
        { provide: HttpClient, useValue: httpClientSpy },
        { provide: RackService, useValue: rackServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    TestBed.overrideComponent(RackComponent, {
      set: {
        providers: [{ provide: RackService, useValue: rackServiceSpy }]
      }
    });

    mockRacks = [
      { uuid: '1', type: 'A' },
      { uuid: '2', type: 'B' }
    ];

    fixture = TestBed.createComponent(RackComponent);
    component = fixture.componentInstance;
    // Mock input
    (component as any).warehouseId = () => 'warehouse-1';

    // Wait for promises to resolve before continuing
    await fixture.whenStable();
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should create the form on init', () => {
    component.ngOnInit();

    expect(component.form).toBeDefined();
    expect(component.form.get('type')).toBeTruthy();
  });

  it('should fetch racks on init', fakeAsync(() => {
    rackServiceSpy.getRacksByWarehouse.and.returnValue(Promise.resolve(mockRacks));
    component.ngOnInit();
    tick();

    expect(component.$racks()).toEqual(mockRacks);
  }));

  it('should handle fetch racks error', fakeAsync(() => {
    rackServiceSpy.getRacksByWarehouse.and.returnValue(Promise.reject('error'));
    spyOn(console, 'error');
    component.ngOnInit();
    tick();

    expect(console.error).toHaveBeenCalledWith('Error fetching racks:', 'error');
  }));

  it('should patch form and set selectedRack on editData', () => {
    const rack = mockRacks[0];
    component['createForm']();
    component.editData(rack);

    expect(component.form.value.type).toBe(rack.type);
    expect((component as any).selectedRack).toBe(rack);
  });

  it('should delete rack and reset form', fakeAsync(() => {
    rackServiceSpy.delete.and.returnValue(Promise.resolve());
    component.$racks.set(mockRacks);
    spyOn(component, 'resetForm');
    component.deleteData('1');
    tick();

    expect(component.$racks()).toEqual([mockRacks[1]]);
    expect(component.resetForm).toHaveBeenCalled();
  }));

  it('should handle delete rack error', fakeAsync(() => {
    rackServiceSpy.delete.and.returnValue(Promise.reject('delete error'));
    spyOn(console, 'error');
    component.deleteData('1');
    tick();

    expect(console.error).toHaveBeenCalledWith('Error deleting rack:', 'delete error');
  }));

  it('should reset form and selectedRack', () => {
    component['createForm']();
    (component as any).selectedRack = mockRacks[0];
    component.resetForm();

    expect(component.form.pristine).toBeTrue();
    expect(component.form.untouched).toBeTrue();
    expect((component as any).selectedRack).toBeNull();
  });

  it('should update rack on submitForm if selectedRack exists', fakeAsync(() => {
    component['createForm']();
    component.form.setValue({ type: 'C' });
    (component as any).selectedRack = { uuid: '1', type: 'A' };
    rackServiceSpy.update.and.returnValue(Promise.resolve({ uuid: '1', type: 'C' }));
    component.$racks.set(mockRacks);
    spyOn(component, 'resetForm');
    component.submitForm();
    tick();

    expect(component.$racks()[0].type).toBe('C');
    expect(component.resetForm).toHaveBeenCalled();
  }));

  it('should handle update rack error', fakeAsync(() => {
    component['createForm']();
    component.form.setValue({ type: 'C' });
    (component as any).selectedRack = { uuid: '1', type: 'A' };
    rackServiceSpy.update.and.returnValue(Promise.reject('update error'));
    spyOn(console, 'error');
    component.submitForm();
    tick();

    expect(console.error).toHaveBeenCalledWith('Error updating rack:', 'update error');
  }));

  it('should create rack on submitForm if selectedRack does not exist', fakeAsync(() => {
    component['createForm']();
    component.form.setValue({ type: 'D' });
    (component as any).selectedRack = null;
    rackServiceSpy.create.and.returnValue(Promise.resolve({ uuid: '3', type: 'D' }));
    component.$racks.set([...mockRacks]);
    spyOn(component, 'resetForm');
    component.submitForm();
    tick();

    expect(component.$racks().length).toBe(3);
    expect(component.$racks()[2].type).toBe('D');
    expect(component.resetForm).toHaveBeenCalled();
  }));

  it('should handle create rack error', fakeAsync(() => {
    component['createForm']();
    component.form.setValue({ type: 'D' });
    (component as any).selectedRack = null;
    rackServiceSpy.create.and.returnValue(Promise.reject('create error'));
    spyOn(console, 'error');
    component.submitForm();
    tick();

    expect(console.error).toHaveBeenCalledWith('Error creating rack:', 'create error');
  }));

  it('should navigate to warehouse detail', () => {
    component.warehouses('abc');

    expect(routerSpy.navigate).toHaveBeenCalledWith(['/warehouse', 'abc']);
  });

  it('should navigate back to warehouse list', () => {
    component.back();

    expect(routerSpy.navigate).toHaveBeenCalledWith(['/warehouse']);
  });
});
