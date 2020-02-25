import { ITranslatedName } from '@/shared/model/translated-name.model';
import { IDocumentationChapter } from '@/shared/model/documentation-chapter.model';

export interface IDocumentationSection {
  id?: number;
  info?: string;
  names?: ITranslatedName[];
  blog?: IDocumentationChapter;
}

export class DocumentationSection implements IDocumentationSection {
  constructor(public id?: number, public info?: string, public names?: ITranslatedName[], public blog?: IDocumentationChapter) {}
}
