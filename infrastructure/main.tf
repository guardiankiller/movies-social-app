terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }

  backend "s3" {
    key                         = "terraform-movies.tfstate"
    bucket                      = "tf-backend-34512"
    region                      = "eu-central-1"
    skip_credentials_validation = true
    skip_metadata_api_check     = true
  }
}

variable "do_token" {}

variable "image_tag" {
  default = "latest"
}

provider "digitalocean" {
  token             = var.do_token
}

resource "digitalocean_app" "demo-app" {
  spec {
    name   = "movies-social-app-m"
    region = "fra"

    service {
      http_port          = 8080
      instance_count     = 1
      instance_size_slug = "apps-s-1vcpu-0.5gb"
      name               = "movies-social-app-m"

      image {
        registry             = "guardiankiller"
        registry_credentials = null # sensitive
        registry_type        = "GHCR"
        repository           = "movies-social-app"
        tag                  = var.image_tag
        deploy_on_push {
          enabled = true
        }
      }
    }
  }
}