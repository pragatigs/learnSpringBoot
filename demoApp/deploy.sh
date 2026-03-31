#!/bin/bash

set -e

echo "Switching to Minikube Docker..."
eval $(minikube docker-env)

echo "Building image..."
docker build -t demo-app:latest .

echo "Deploying with Helm..."
cd demo-app
helm upgrade --install demo-app . -n demo-test --create-namespace

echo "Restarting deployment..."
kubectl rollout restart deployment demo-app -n demo-test

echo "Pods:"
kubectl get pods -n demo-test

echo "Logs:"
kubectl logs -l app=spring-app -n demo-test --tail=50

echo "Done!"
