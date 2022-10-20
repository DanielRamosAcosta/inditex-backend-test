#!/usr/bin/env bash

curl -X POST localhost:8080/products -H 'Content-Type: application/json' -d '{"name": "V-NECH BASIC SHIRT"}' | jq
# curl -X POST localhost:8080/products -H 'Content-Type: application/json' -d '{"name": "CONTRASTING FABRIC T-SHIRT"}' | jq
# curl -X POST localhost:8080/products -H 'Content-Type: application/json' -d '{"name": "RAISED PRINT T-SHIRT"}' | jq
# curl -X POST localhost:8080/products -H 'Content-Type: application/json' -d '{"name": "PLEATED T-SHIRT"}' | jq
# curl -X POST localhost:8080/products -H 'Content-Type: application/json' -d '{"name": "CONTRASTING LACE T-SHIRT"}' | jq
# curl -X POST localhost:8080/products -H 'Content-Type: application/json' -d '{"name": "SLOGAN T-SHIRT"}' | jq
#
# curl localhost:8080/products | jq
#
# curl -X POST localhost:8080/orders -H 'Content-Type: application/json' -d '[{ "productId": 1, "variantId": 1, "amount": 2 }]' | jq
#