export const enum Language {
  FR = 'FR',
  EN = 'EN',
  ES = 'ES',
  IT = 'IT',
  RO = 'RO',
  DU = 'DU',
  NL = 'NL'
}

export interface IFaqEntry {
  id?: number;
  language?: Language;
  title?: string;
  content?: any;
  date?: Date;
}

export class FaqEntry implements IFaqEntry {
  constructor(public id?: number, public language?: Language, public title?: string, public content?: any, public date?: Date) {}
}
