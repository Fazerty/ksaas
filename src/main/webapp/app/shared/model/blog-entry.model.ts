import { ITag } from '@/shared/model/tag.model';

export const enum Language {
  FR = 'FR',
  EN = 'EN',
  ES = 'ES',
  IT = 'IT',
  RO = 'RO',
  DU = 'DU',
  NL = 'NL'
}

export interface IBlogEntry {
  id?: number;
  language?: Language;
  title?: string;
  content?: any;
  date?: Date;
  tags?: ITag[];
}

export class BlogEntry implements IBlogEntry {
  constructor(
    public id?: number,
    public language?: Language,
    public title?: string,
    public content?: any,
    public date?: Date,
    public tags?: ITag[]
  ) {}
}
