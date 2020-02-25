export const enum Language {
  FR = 'FR',
  EN = 'EN',
  ES = 'ES',
  IT = 'IT',
  RO = 'RO',
  DU = 'DU',
  NL = 'NL'
}

export interface IDocumentationEntry {
  id?: number;
  language?: Language;
  title?: string;
  content?: any;
  date?: Date;
}

export class DocumentationEntry implements IDocumentationEntry {
  constructor(public id?: number, public language?: Language, public title?: string, public content?: any, public date?: Date) {}
}
