import { ITranslatedName } from '@/shared/model/translated-name.model';

export interface IFaqSection {
  id?: number;
  info?: string;
  names?: ITranslatedName[];
}

export class FaqSection implements IFaqSection {
  constructor(public id?: number, public info?: string, public names?: ITranslatedName[]) {}
}
