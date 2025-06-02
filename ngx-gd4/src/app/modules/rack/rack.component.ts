import { CommonModule } from '@angular/common';
import { Component, inject, input, signal, WritableSignal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { uuid } from '../../core/lib/key.lib';
import { IRackModel } from '../../core/models/rack.model';
import { RackService } from '../../core/services/rack.service';

@Component({
  selector: 'app-rack',
  templateUrl: './rack.component.html',
  styleUrl: './rack.component.scss',
  imports: [CommonModule, ReactiveFormsModule],
  providers: [RackService]
})
export class RackComponent {
  public form!: FormGroup;
  public warehouseId = input.required<string>();
  public $racks: WritableSignal<IRackModel[]> = signal([]);

  private selectedRack: IRackModel | null = null;

  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly rackService = inject(RackService);

  ngOnInit(): void {
    this.createForm();

    this.fetchRacks();
  }

  public editData(rack: IRackModel): void {
    this.form.patchValue({
      type: rack.type
    });

    this.selectedRack = rack;
  }

  public deleteData(uudi: string): void {
    this.rackService
      .delete(uudi)
      .then(() => {
        this.$racks.set(this.$racks().filter(w => w.uuid !== uudi));

        this.resetForm();
      })
      .catch(error => {
        console.error('Error deleting rack:', error);
      });
  }

  public resetForm(): void {
    this.form.reset();
    this.form.markAsPristine();
    this.form.markAsUntouched();
    this.form.updateValueAndValidity();

    this.selectedRack = null;
  }

  public submitForm(): void {
    console.log('Form submitted:', this.form.value);

    if (this.selectedRack) {
      this.rackService
        .update(this.warehouseId(), { ...this.selectedRack, ...this.form.value })
        .then(updatedrack => {
          const updatedracks = this.$racks().map(w => (w.uuid === updatedrack.uuid ? updatedrack : w));
          this.$racks.set(updatedracks);

          this.resetForm();
        })
        .catch(error => {
          console.error('Error updating rack:', error);
        });
    } else {
      this.rackService
        .create(this.warehouseId(), { ...this.form.value, uuid: uuid() })
        .then(newrack => {
          this.$racks.set([...this.$racks(), newrack]);
          this.resetForm();
        })
        .catch(error => {
          console.error('Error creating rack:', error);
        });
    }
  }

  public warehouses(uuid: string): void {
    this.router.navigate(['/warehouse', uuid]);
  }

  public back() {
    this.router.navigate(['/warehouse']);
  }

  private fetchRacks(): void {
    console.log('Fetching racks for warehouse:', this.warehouseId());

    this.rackService
      .getRacksByWarehouse(this.warehouseId(), 0, 10)
      .then(racks => {
        console.log('Fetched racks:', racks);
        this.$racks.set(racks);
      })
      .catch(error => {
        console.error('Error fetching racks:', error);
      });
  }

  private createForm() {
    this.form = this.fb.group({
      type: ['', [Validators.required]]
    });
  }
}
