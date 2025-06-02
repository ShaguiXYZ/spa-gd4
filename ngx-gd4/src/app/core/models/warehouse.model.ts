export type WarehouseFamilyEnum = 'EST' | 'ROB';

export interface IWarehouseDTO {
  id: number;
  uuid: string;
  client: string;
  size: number;
  family: WarehouseFamilyEnum;
}

export interface IWarehouseModel {
  id: number;
  uuid: string;
  client: string;
  size: number;
  family: WarehouseFamilyEnum;
}

export namespace IWarehouseDTO {
  export function fromModel(model: IWarehouseModel): IWarehouseDTO {
    return {
      id: model.id,
      uuid: model.uuid,
      client: model.client,
      size: model.size,
      family: model.family
    };
  }

  export function toModel(dto: IWarehouseDTO): IWarehouseModel {
    return {
      id: dto.id,
      uuid: dto.uuid,
      client: dto.client,
      size: dto.size,
      family: dto.family
    };
  }
}
