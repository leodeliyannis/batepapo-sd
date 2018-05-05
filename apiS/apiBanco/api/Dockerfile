FROM keymetrics/pm2:latest-jessie

RUN apt-get install -y curl

# Node e npm mais recentes 
RUN apt-get install -y build-essential libssl-dev
RUN curl -sL https://deb.nodesource.com/setup_9.x | bash -
RUN apt-get install -y nodejs

# Bundle APP files
COPY src src/
COPY pm2.json .

# Install app dependencies

ENV NPM_CONFIG_LOGLEVEL warn
RUN cd /src npm install

# Show current folder structure in logs
RUN ls -al -R

CMD [ "pm2-runtime", "start", "pm2.json" ]
