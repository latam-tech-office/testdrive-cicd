selection="{.items[?(.spec.containers[*].name==\"${1}\")].metadata.name}"
oc port-forward $(oc get pods --output jsonpath=${selection}) 27017:27017
