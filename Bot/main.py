import random

from telegram import Update
from telegram.ext import Updater
from telegram.ext import CommandHandler
from telegram.ext import MessageHandler
from telegram.ext import Filters
from telegram import ReplyKeyboardMarkup

BUTTON_MAGIC = "What's the plan?"

LINES = []

reply_keyboard = [[BUTTON_MAGIC]]
markup = ReplyKeyboardMarkup(reply_keyboard)

def do_start(update: Update, context):
    print("get start")
    update.message.reply_text('Hello! I see you need a break. Rather, press the button to find out ...', reply_markup=markup)
    print("send start")

def do_help(update: Update, context):
    print("get help")
    update.message.reply_text("Press button to see action")
    print("send help")

def do_message(update: Update, context):
    if update.message.text == BUTTON_MAGIC:
        print("get magic")
        text = random.choice(LINES).split('|', maxsplit=1)
        update.message.reply_photo(open(text[0] + ".png", "rb"))
        update.message.reply_text(text[1])
        print("send magic")
        return
    return do_help(update=update, context=context)

def main():
    TG_TOKEN = "970096464:AAHHH0lacxLPJW4ga8ziUp5wtP9dLO8fsqI"
    REQUEST_KWARGS = {
        'proxy_url': 'socks5h://139.59.90.148:20183'
        }
    updater = Updater(TG_TOKEN, request_kwargs=REQUEST_KWARGS, use_context=True)
    start_handler = CommandHandler("start", do_start)
    help_handler = CommandHandler("help", do_help)
    message_handler = MessageHandler(Filters.text, do_message)
    updater.dispatcher.add_handler(start_handler)
    updater.dispatcher.add_handler(message_handler)
    updater.dispatcher.add_handler(help_handler)

    file = open("exercises.txt", encoding='utf-8')
    global LINES
    LINES = file.readlines()
    updater.start_polling()

    print("start bot")
    updater.idle()

if __name__ == '__main__':
    main()