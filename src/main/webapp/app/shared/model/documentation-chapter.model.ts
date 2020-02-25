import { ITranslatedName } from '@/shared/model/translated-name.model';

export interface IDocumentationChapter {
  id?: number;
  info?: string;
  names?: ITranslatedName[];
}

export class DocumentationChapter implements IDocumentationChapter {
  constructor(public id?: number, public info?: string, public names?: ITranslatedName[]) {}
}
