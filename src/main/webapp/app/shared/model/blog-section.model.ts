import { ITranslatedName } from '@/shared/model/translated-name.model';

export interface IBlogSection {
  id?: number;
  info?: string;
  names?: ITranslatedName[];
}

export class BlogSection implements IBlogSection {
  constructor(public id?: number, public info?: string, public names?: ITranslatedName[]) {}
}
