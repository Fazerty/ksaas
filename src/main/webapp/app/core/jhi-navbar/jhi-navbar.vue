<template>
    <b-navbar toggleable="md" type="dark" class="bg-primary">
        <div class="jh-logo-container float-left">
            <b-navbar-toggle right class="jh-navbar-toggler d-lg-none float-right" href="javascript:void(0);"  data-toggle="collapse" target="header-tabs" aria-expanded="false" aria-label="Toggle navigation">
                <font-awesome-icon class="navicon" icon="bars" />
            </b-navbar-toggle>
            <b-navbar-brand class="logo float-left" b-link to="/">
                <span class="logo-img"></span>
                <span v-text="$t('global.title')" class="navbar-title">Upurion</span>
                <!-- <span class="navbar-version">{{version}}</span> -->
            </b-navbar-brand>
        </div>
        <b-collapse is-nav id="header-tabs">
            <b-navbar-nav class="ml-auto">
                <b-nav-item-dropdown
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN')"
                    :class="{'router-link-active': subIsActive('/admin')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon class="navicon" icon="user-plus" />
                        <span v-text="$t('global.menu.admin.main')">Administration</span>
                    </span>
                    <b-dropdown-item to="/admin/user-management">
                        <font-awesome-icon class="navicon" icon="user" />
                        <span v-text="$t('global.menu.admin.userManagement')">User management</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-metrics">
                        <font-awesome-icon class="navicon" icon="tachometer-alt" />
                        <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/jhi-health">
                        <font-awesome-icon class="navicon" icon="heart" />
                        <span v-text="$t('global.menu.admin.health')">Health</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-configuration">
                        <font-awesome-icon class="navicon" icon="list" />
                        <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/audits">
                        <font-awesome-icon class="navicon" icon="bell" />
                        <span v-text="$t('global.menu.admin.audits')">Audits</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/logs">
                        <font-awesome-icon class="navicon" icon="tasks" />
                        <span v-text="$t('global.menu.admin.logs')">Logs</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs">
                        <font-awesome-icon class="navicon" icon="book" />
                        <span v-text="$t('global.menu.admin.apidocs')">API</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
                    <span slot="button-content">
                        <font-awesome-icon class="navicon" icon="flag" />
                        <span v-text="$t('global.menu.language')">Language</span>
                    </span>
                    <b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`" v-on:click="changeLanguage(key);"
                        :class="{ active: isActiveLanguage(key)}">
                        {{value.name}}
                    </b-dropdown-item>
                </b-nav-item-dropdown>

                <!-- Needed for jhipster -->
                <b-nav-item-dropdown
                    id="entity-menu"
                    v-if="authenticated"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="th-list" />
                        <span v-text="$t('global.menu.entities.main')">Entities</span>
                    </span>
                    <b-dropdown-item to="/blog-section">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.blogSection')">BlogSection</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/blog-entry-group">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.blogEntryGroup')">BlogEntryGroup</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/blog-entry">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.blogEntry')">BlogEntry</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/tag">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.tag')">Tag</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/faq-section">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.faqSection')">FaqSection</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/faq-entry-group">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.faqEntryGroup')">FaqEntryGroup</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/faq-entry">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.faqEntry')">FaqEntry</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/documentation-chapter">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.documentationChapter')">DocumentationChapter</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/documentation-section">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.documentationSection')">DocumentationSection</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/documentation-entry-group">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.documentationEntryGroup')">DocumentationEntryGroup</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/documentation-entry">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.documentationEntry')">DocumentationEntry</span>
                    </b-dropdown-item>
                    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
                </b-nav-item-dropdown>


                <b-nav-item-dropdown
                    right
                    id="account-menu"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon class="navicon" icon="user" />
                        <span v-text="$t('global.menu.features.main')">
                            Features
                        </span>
                    </span>
                    <b-dropdown-item to="/features/common" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="wrench" />
                        <span v-text="$t('global.menu.features.common')">Common</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/features/webapp" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="lock" />
                        <span v-text="$t('global.menu.features.webApp')">Web application</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/features/desktop" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="lock" />
                        <span v-text="$t('global.menu.features.desktop')">Desktop</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item to="/pricing" exact>
                    <span>
                        <font-awesome-icon class="navicon" icon="home" />
                        <span v-text="$t('global.menu.pricing')">Pricing</span>
                    </span>
                </b-nav-item>
                <b-nav-item-dropdown
                    right
                    id="account-menu"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon class="navicon" icon="user" />
                        <span v-text="$t('global.menu.community.main')">
                            Community
                        </span>
                    </span>
                    <b-dropdown-item to="/community/tutorials" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="wrench" />
                        <span v-text="$t('global.menu.community.tutorials')">Tutorials</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/community/getInvolved" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="lock" />
                        <span v-text="$t('global.menu.community.getInvolved')">Get involved</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>

                <b-nav-item-dropdown
                    right
                    id="account-menu"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon class="navicon" icon="user" />
                        <span v-text="$t('global.menu.help.main')">
                            Help
                        </span>
                    </span>
                    <b-dropdown-item to="/help/documentation" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="wrench" />
                        <span v-text="$t('global.menu.help.documentation')">Documentation</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/help/faq" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="lock" />
                        <span v-text="$t('global.menu.help.faq')">F.A.Q.</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/help/blog" tag="b-dropdown-item">
                        <font-awesome-icon class="navicon" icon="lock" />
                        <span v-text="$t('global.menu.help.blog')">blog</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>

                <b-nav-item-dropdown
                    right
                    href="javascript:void(0);"
                    id="account-menu"
                    :class="{'router-link-active': subIsActive('/account')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon class="navicon" icon="user" />
                        <span v-text="$t('global.menu.account.main')">
                            Account
                        </span>
                    </span>
                    <b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated">
                        <font-awesome-icon class="navicon" icon="wrench" />
                        <span v-text="$t('global.menu.account.settings')">Settings</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated">
                        <font-awesome-icon class="navicon" icon="lock" />
                        <span v-text="$t('global.menu.account.password')">Password</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="authenticated"  v-on:click="logout()" id="logout">
                        <font-awesome-icon class="navicon" icon="sign-out-alt" />
                        <span v-text="$t('global.menu.account.logout')">Sign out</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="!authenticated"  v-on:click="openLogin()" id="login">
                        <font-awesome-icon class="navicon" icon="sign-in-alt" />
                        <span v-text="$t('global.menu.account.login')">Sign in</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated">
                        <font-awesome-icon class="navicon" icon="user-plus" />
                        <span v-text="$t('global.menu.account.register')">Register</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 9px;
}

nav {
    font-size: 90%;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
.navicon {
    display: none;
}
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span{
    display:none;
  }
.navicon {
    display: inline;
}
}

@media screen and (max-width: 767px) {
  .jh-logo-container {
    width: 100%;
  }

}

.navbar-title {
  display: inline-block;
  font-size: 150%;
  font-family: Times, "Times New Roman", serif;
  font-weight: bold;
  font-style: italic;
  vertical-align: middle;
}
/* waiting for bootstrap fix bug on nav-item-dropdown a:active
https://github.com/bootstrap-vue/bootstrap-vue/issues/2219
*/
nav li.router-link-active .navbar-dropdown-menu {
  cursor: pointer;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 1px 5px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 90%;
  background: url("../../../content/images/logo.png") no-repeat center
    center;
  background-size: contain;
  width: 90%;
}
</style>
