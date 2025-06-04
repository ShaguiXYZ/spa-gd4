import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IWarehouseModel } from '../../core/models/warehouse.model';
import { WarehouseService } from '../../core/services/warehouse.service';
import { uuid } from '../../core/lib/key.lib';
import { Router } from '@angular/router';

@Component({
  selector: 'app-warehouse',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './warehouse.component.html',
  styleUrl: './warehouse.component.scss',
  providers: [WarehouseService]
})
export class WarehouseComponent implements OnInit {
  public form!: FormGroup;
  public $warehouses: WritableSignal<IWarehouseModel[]> = signal([]);

  private selectedWarehouse?: IWarehouseModel;

  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly warehouseService = inject(WarehouseService);

  ngOnInit(): void {
    this.createForm();

    this.warehouseService
      .getWarehouses(0, 10)
      .then(warehouses => {
        console.log('Fetched warehouses:', warehouses);

        this.$warehouses.set(warehouses);
      })
      .catch(error => {
        console.error('Error fetching warehouses:', error);
      });
  }

  public editData(warehouse: IWarehouseModel): void {
    this.form.patchValue({
      client: warehouse.client,
      size: warehouse.size,
      family: warehouse.family
    });

    this.selectedWarehouse = warehouse;
  }

  public deleteData(uudi: string): void {
    this.warehouseService
      .delete(uudi)
      .then(() => {
        this.$warehouses.set(this.$warehouses().filter(w => w.uuid !== uudi));

        this.resetForm();
      })
      .catch(error => {
        console.error('Error deleting warehouse:', error);
      });
  }

  public resetForm(): void {
    this.form.reset();
    this.form.markAsPristine();
    this.form.markAsUntouched();
    this.form.updateValueAndValidity();

    this.selectedWarehouse = undefined;
  }

  public submitForm(): void {
    console.log('Form submitted:', this.form.value);

    if (this.selectedWarehouse) {
      this.warehouseService
        .update({ ...this.selectedWarehouse, ...this.form.value })
        .then(updatedWarehouse => {
          const updatedWarehouses = this.$warehouses().map(w => (w.uuid === updatedWarehouse.uuid ? updatedWarehouse : w));
          this.$warehouses.set(updatedWarehouses);

          this.resetForm();
        })
        .catch(error => {
          console.error('Error updating warehouse:', error);
        });
    } else {
      this.warehouseService
        .create({ ...this.form.value, uuid: uuid() })
        .then(newWarehouse => {
          this.$warehouses.set([...this.$warehouses(), newWarehouse]);
          this.resetForm();
        })
        .catch(error => {
          console.error('Error creating warehouse:', error);
        });
    }
  }

  public racks(uuid: string): void {
    console.log('Navigating to racks for warehouse:', uuid);
    this.router.navigate(['/rack', uuid]);
  }

  private createForm() {
    this.form = this.fb.group({
      client: ['', [Validators.required]],
      size: [0, [Validators.required]],
      family: ['', [Validators.required]]
    });
  }
}
