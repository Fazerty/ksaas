import { IDocumentationSection } from '@/shared/model/documentation-section.model';
import { IFaqEntry } from '@/shared/model/faq-entry.model';

export interface IDocumentationEntryGroup {
  id?: number;
  blog?: IDocumentationSection;
  entry?: IFaqEntry;
}

export class DocumentationEntryGroup implements IDocumentationEntryGroup {
  constructor(public id?: number, public blog?: IDocumentationSection, public entry?: IFaqEntry) {}
}
