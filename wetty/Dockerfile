FROM mhart/alpine-node:6
MAINTAINER Mauricio "Maltron" Leal <maltron at gmail dot com>

LABEL io.k8s.description="Wetty for TestDrive" \
      io.k8s.display-name="TestDrive SSH" \
      io.openshift.expose-services="3000:testdrive" \
      io.openshift.tags="testdrive,wetty,ssh" 

ENV TESTDRIVE_HOME=/home/testdrive \
    GROUP_ID=1001 \
    USER_ID=1001 \
    USER=testdrive \
    REMOTE_SSH_SERVER=127.0.0.1 \
    REMOTE_SSH_PORT=22 \
    REMOTE_SSH_USER=root

RUN addgroup -S ${USER} -g ${GROUP_ID} && adduser -u ${USER_ID} -S -G ${USER} -h /home/${USER} ${USER} && chmod 755 /home/${USER} && \
    apk update && apk add --no-cache git openssh-client python make g++ && \
    git clone https://github.com/krishnasrinivas/wetty ${TESTDRIVE_HOME} && \
    cd ${TESTDRIVE_HOME} && npm install && \
    apk del -r python make g++ && \
    chown -R ${USER}:${USER} ${TESTDRIVE_HOME} && chgrp -R ${USER} ${TESTDRIVE_HOME}

WORKDIR ${TESTDRIVE_HOME}
EXPOSE 3000
USER ${USER_ID}
ENTRYPOINT node app.js -p 3000 --sshhost $REMOTE_SSH_SERVER --sshport $REMOTE_SSH_PORT --sshuser $REMOTE_SSH_USER
