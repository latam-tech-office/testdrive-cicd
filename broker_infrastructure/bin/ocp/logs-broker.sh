oc logs --follow $(oc get pods --output jsonpath='{.items[?(.metadata.labels.deploymentConfig=="broker-amq")].metadata.name}') --namespace broker
