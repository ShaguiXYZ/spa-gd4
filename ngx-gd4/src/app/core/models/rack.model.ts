export type RackTypeEnum = 'A' | 'B' | 'C' | 'D';

export interface IRackDTO {
  uuid: string;
  type: RackTypeEnum;
}

export interface IRackModel {
  uuid: string;
  type: RackTypeEnum;
}

export namespace IRackDTO {
  export function fromModel(model: IRackModel): IRackDTO {
    return {
      uuid: model.uuid,
      type: model.type
    };
  }

  export function toModel(dto: IRackDTO): IRackModel {
    return {
      uuid: dto.uuid,
      type: dto.type
    };
  }
}
