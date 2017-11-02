oc port-forward $(oc get pods --output jsonpath='{.items[?(.spec.containers[*].name=="mongodb")].metadata.name}') 27017:27017
