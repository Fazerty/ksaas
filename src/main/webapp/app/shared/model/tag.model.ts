import { ITranslatedName } from '@/shared/model/translated-name.model';
import { IBlogEntry } from '@/shared/model/blog-entry.model';

export interface ITag {
  id?: number;
  names?: ITranslatedName[];
  entries?: IBlogEntry[];
}

export class Tag implements ITag {
  constructor(public id?: number, public names?: ITranslatedName[], public entries?: IBlogEntry[]) {}
}
