#!/bin/bash

# Variáveis
VM_NAME="vm_ondetamoto"
RESOURCE_GROUP="rg-ondetamoto"
USERNAME="rm557462"
PASSWORD="Guilherme@080606"
LOCATION="eastus"
IMAGE="Canonical:0001-com-ubuntu-server-focal:20_04-lts:latest"
VM_SIZE="Standard_B2s"

az group create --name $RESOURCE_GROUP --location $LOCATION

az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image $IMAGE \
  --admin-username $USERNAME \
  --admin-password $PASSWORD \
  --size $VM_SIZE \
  --public-ip-sku Basic \
  --output json

az vm open-port --resource-group $RESOURCE_GROUP --name $VM_NAME --port 8080

