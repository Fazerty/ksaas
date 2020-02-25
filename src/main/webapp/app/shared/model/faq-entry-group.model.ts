import { IFaqSection } from '@/shared/model/faq-section.model';
import { IFaqEntry } from '@/shared/model/faq-entry.model';

export interface IFaqEntryGroup {
  id?: number;
  blog?: IFaqSection;
  entry?: IFaqEntry;
}

export class FaqEntryGroup implements IFaqEntryGroup {
  constructor(public id?: number, public blog?: IFaqSection, public entry?: IFaqEntry) {}
}
