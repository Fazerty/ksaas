import { IUser } from '@/shared/model/user.model';
import { IBlogSection } from '@/shared/model/blog-section.model';
import { IBlogEntry } from '@/shared/model/blog-entry.model';

export interface IBlogEntryGroup {
  id?: number;
  user?: IUser;
  blog?: IBlogSection;
  entry?: IBlogEntry;
}

export class BlogEntryGroup implements IBlogEntryGroup {
  constructor(public id?: number, public user?: IUser, public blog?: IBlogSection, public entry?: IBlogEntry) {}
}
