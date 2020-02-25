import { ITag } from '@/shared/model/tag.model';
import { IBlogSection } from '@/shared/model/blog-section.model';
import { IFaqSection } from '@/shared/model/faq-section.model';
import { IDocumentationChapter } from '@/shared/model/documentation-chapter.model';
import { IDocumentationSection } from '@/shared/model/documentation-section.model';

export enum Language {
  FR = 'FR',
  EN = 'EN',
  ES = 'ES',
  IT = 'IT',
  RO = 'RO',
  DU = 'DU',
  NL = 'NL'
}

export interface ITranslatedName {
  id?: number;
  language?: Language;
  name?: string;
  slug?: string;
  tag?: ITag;
  blogSection?: IBlogSection;
  faqSection?: IFaqSection;
  documentationChapter?: IDocumentationChapter;
  documentationSection?: IDocumentationSection;
}

export class TranslatedName implements ITranslatedName {
  constructor(
    public id?: number,
    public language?: Language,
    public name?: string,
    public slug?: string,
    public tag?: ITag,
    public blogSection?: IBlogSection,
    public faqSection?: IFaqSection,
    public documentationChapter?: IDocumentationChapter,
    public documentationSection?: IDocumentationSection
  ) {}
}
