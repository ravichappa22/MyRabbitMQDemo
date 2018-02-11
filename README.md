Advanced Message Queuing Protocol (AMQP) in Brief
AMQP is a widely accepted open-source standard for distributing and transferring messages from a source to a destination. As a protocol and standard, it sets a common ground for various applications and message broker middlewares to interoperate without encountering issues caused by individually set design decisions.

Installing RabbitMQ
RabbitMQ packages are distributed both with CentOS / RHEL & Ubuntu / Debian based systems. However, they are - like with most applications - outdated. The recommended way to get RabbitMQ on your system is therefore to download the package online and install manually.

Note: We will be performing our installations and perform the actions listed here on a fresh and newly created VPS due to various reasons. If you are actively serving clients and might have modified your system, in order to not break anything working and to not to run into issues, you are highly advised to try the following instructions on a new system.

Installing on CentOS 6 / RHEL Based Systems
Before installing RabbitMQ, we need to get its main dependencies such as Erlang. However, first and foremost we should update our system and its default applications.

Run the following to update our droplet:

yum -y update
And let's use the below commands to get Erlang on our system:

# Add and enable relevant application repositories:
# Note: We are also enabling third party remi package repositories.
wget http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm
wget http://rpms.famillecollet.com/enterprise/remi-release-6.rpm
sudo rpm -Uvh remi-release-6*.rpm epel-release-6*.rpm

# Finally, download and install Erlang:
yum install -y erlang
Once we have Erlang, we can continue with installing RabbitMQ:

# Download the latest RabbitMQ package using wget:
wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.2.2/rabbitmq-server-3.2.2-1.noarch.rpm

# Add the necessary keys for verification:
rpm --import http://www.rabbitmq.com/rabbitmq-signing-key-public.asc

# Install the .RPM package using YUM:
yum install rabbitmq-server-3.2.2-1.noarch.rpm
Installing on Ubuntu 13 / Debian 7 Based Systems
The process for downloading and installing RabbitMQ on Ubuntu and Debian will be similar to CentOS due to our desire of having a more recent version.

Let's begin with updating our system's default application toolset:

apt-get    update 
apt-get -y upgrade
Enable RabbitMQ application repository:

echo "deb http://www.rabbitmq.com/debian/ testing main" >> /etc/apt/sources.list
Add the verification key for the package:

curl http://www.rabbitmq.com/rabbitmq-signing-key-public.asc | sudo apt-key add -
Update the sources with our new addition from above:

apt-get update
And finally, download and install RabbitMQ:

sudo apt-get install rabbitmq-server
In order to manage the maximum amount of connections upon launch, open up and edit the following configuration file using nano:

sudo nano /etc/default/rabbitmq-server
Uncomment the limit line (i.e. remove #) before saving and exit by pressing CTRL+X followed with Y.

Managing RabbitMQ
As we have mentioned before, RabbitMQ is very simple to get started with. Using the instructions below for your system, you can quickly manage its process and have it running at the system start-up (i.e. boot).

Enabling the Management Console
RabbitMQ Management Console is one of the available plugins that lets you monitor the [RabbitMQ] server process through a web-based graphical user interface (GUI).

Using this console you can:

Manage exchanges, queues, bindings, users
Monitor queues, message rates, connections
Send and receive messages
Monitor Erlang processes, memory usage
And much more
To enable RabbitMQ Management Console, run the following:

sudo rabbitmq-plugins enable rabbitmq_management
Once you've enabled the console, it can be accessed using your favourite web browser by visiting: http://[your droplet's IP]:15672/.

The default username and password are both set “guest” for the log in.

Note: If you enable this console after running the service, you will need to restart it for the changes to come into effect. See the relevant management section below for your operating system to be able to do it.

Managing on CentOS / RHEL Based Systems
Upon installing the application, RabbitMQ is not set to start at system boot by default.

To have RabbitMQ start as a daemon by default, run the following:

chkconfig rabbitmq-server on
To start, stop, restart and check the application status, use the following:

# To start the service:
/sbin/service rabbitmq-server start

# To stop the service:
/sbin/service rabbitmq-server stop

# To restart the service:
/sbin/service rabbitmq-server restart

# To check the status:
/sbin/service rabbitmq-server status
Managing on Ubuntu / Debian Based Systems
To start, stop, restart and check the application status on Ubuntu and Debian, use the following:

# To start the service:
service rabbitmq-server start

# To stop the service:
service rabbitmq-server stop

# To restart the service:
service rabbitmq-server restart

# To check the status:
service rabbitmq-server status
And that's it! You now have your own message queue working on your virtual server.

Configuring RabbitMQ
RabbitMQ by default runs with its standard configuration. In general, it does not require much tempering with for most needs as long as everything runs smoothly.