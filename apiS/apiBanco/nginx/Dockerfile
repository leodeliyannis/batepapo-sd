FROM nginx

COPY nginx.conf /etc/nginx/nginx.conf

RUN mkdir /log && mkdir /log/nginx
RUN ln -sf /dev/stdout /log/nginx/error_stdout.log
RUN ln -sf /dev/stderr /log/nginx/access_stderr.log