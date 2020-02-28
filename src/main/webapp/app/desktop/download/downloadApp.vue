<i18n>
{
  "en": {
    "download": {
      "terms": "By downloading, you agree to the Terms and Conditions.",
      "linux" : {
        "deb": "Download .deb",
        "portable": "Download portable version"
        },
      "windows" : {
        "installer": "Download installer",
        "portable": "Download portable version"
        },
      "mac" : {
        "installer": "Download installer",
        "portable": "Download portable version"
        }
    }
  },
  "fr": {
    "download": {
      "terms": "En téléchargeant, vous acceptez les conditions générales.",
      "linux" : {
        "deb": "Télécharger .deb",
        "portable": "Télécharger la version portable"
        },
      "windows" : {
        "installer": "Télécharger l'installeur",
        "portable": "Télécharger la version portable"
        },
      "mac" : {
        "installer": "Télécharger l'installeur",
        "portable": "Télécharger la version portable"
        }
    }
  }
}
</i18n>
<template>
  <b-container>
    <b-row>
      <b-col cols="6"></b-col>
      <b-col>
        <h3 class="ml-5 mt-5 text-warning">!!! -Prototypes- !!!</h3>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6">
        <span class="download img-fluid rounded"></span>
      </b-col>
      <b-col cols="6">
        <b-card no-body>
          <b-tabs content-class="mt-3" pills card>
            <b-tab title="Linux" active>
              <b-card-text>
                <b-container flex class="ml-5 mt-5 text-secondary">
                  <b-row>
                    <h3>0.1.0-alpha</h3>
                  </b-row>
                  <b-row>
                    <b-button size="lg" class="mt-5" href="/content/downloads/Upurion-0.1.0-alpha-amd64.deb">
                      <font-awesome-icon icon="download"></font-awesome-icon>
                      <span v-text="$t('download.linux.deb')">Download linux deb</span>
                    </b-button>
                  </b-row>

                  <b-row>
                    <b-button size="lg" class="mt-5 mb-5" href="/content/downloads/Upurion-0.1.0-alpha-x86_64.AppImage">
                      <font-awesome-icon icon="download"></font-awesome-icon>
                      <span v-text="$t('download.linux.portable')">Download linux portable</span>
                    </b-button>
                  </b-row>
                </b-container>
              </b-card-text>
            </b-tab>
            <b-tab title="Windows">
              <b-card-text>
                <b-container flex class="ml-5 mt-5 text-secondary">
                  <b-row>
                    <h3>0.1.0-alpha</h3>
                  </b-row>
                  <b-row>
                    <b-button size="lg" class="mt-5" href="/content/downloads/Upurion-Web-Setup-0.1.0-alpha.exe">
                      <font-awesome-icon icon="download"></font-awesome-icon>
                      <span v-text="$t('download.windows.installer')">Download windows installer</span>
                    </b-button>
                  </b-row>

                  <b-row>
                    <b-button size="lg" class="mt-5 mb-5" href="/content/downloads/Upurion-0.1.0-alpha-portable.exe">
                      <font-awesome-icon icon="download"></font-awesome-icon>
                      <span v-text="$t('download.windows.portable')">Download windows portable</span>
                    </b-button>
                  </b-row>
                </b-container>
              </b-card-text>
            </b-tab>
            <b-tab title="Mac" disabled>
              <b-card-text>
                <b-container flex class="ml-5 mt-5 text-secondary">
                  <b-row>
                    <h3>0.1.0-alpha</h3>
                  </b-row>
                  <b-row>
                    <b-button size="lg" class="mt-5" href="/content/downloads/">
                      <font-awesome-icon icon="download"></font-awesome-icon>
                      <span v-text="$t('download.mac.installer')">Download mac deb</span>
                    </b-button>
                  </b-row>

                  <b-row>
                    <b-button size="lg" class="mt-5 mb-5" href="/content/downloads/">
                      <font-awesome-icon icon="download"></font-awesome-icon>
                      <span v-text="$t('download.mac.portable')">Download mac portable</span>
                    </b-button>
                  </b-row>
                </b-container>
              </b-card-text>
            </b-tab>
          </b-tabs>

        <div class="ml-5 text-secondary font-weight-light text-justify" v-text="$t('download.terms')">By downloading, you agree to the Terms and Conditions. </div>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./downloadApp.component.ts">
</script>
<style lang="scss" scoped>
</style>
