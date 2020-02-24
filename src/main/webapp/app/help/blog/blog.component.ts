import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import BlogSectionSideBar from '../../entities/blog-section/blog-section-sidebar.vue';

@Component({
  components: {
    BlogSectionSideBar
  }
})
export default class Blog extends Vue {}
